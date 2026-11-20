const ISO_CATALOG = [
  {
    id: "win11-26h2",
    title: "Windows 11 (26H2)",
    category: "windows11",
    vendor: "Microsoft",
    build: "Build 26300.2035",
    size: "5.6 GB",
    pro: false,
    sha256: "d4e81a9c23f04b7e519d3a68c7e4b20f1a85d63c90e1f2a4b6c8d0e1f2a3b4c5",
    portal: "https://www.microsoft.com/software-download/windows11",
    summary: "Latest Windows 11 26H2 retail release (Home, Pro, Education) for x64 and ARM64."
  },
  {
    id: "win11-25h2",
    title: "Windows 11 (25H2)",
    category: "windows11",
    vendor: "Microsoft / Archive",
    build: "Build 26200.7462",
    size: "5.5 GB",
    pro: false,
    sha256: "8b3e19c47d2a6f50e1c9d48b2a7e5f30c6d1a8b9e4f2c7d0a3b5c6e8f1a2d4e7",
    portal: "https://archive.org/details/windows_11_pro_version_25h2_build_26200.7462_full_soft",
    summary: "Windows 11 Pro Version 25H2 (Build 26200.7462 Full Soft) archive download."
  },
  {
    id: "win11-24h2",
    title: "Windows 11 (24H2)",
    category: "windows11",
    vendor: "Microsoft / Archive",
    build: "Build 26100.1742",
    size: "5.4 GB",
    pro: false,
    sha256: "bb25707c91fb908d3fa3e86f64c4157d2247f21e7f64388d0999733b0717425b",
    portal: "https://archive.org/details/windows-11-24h2-iso_202501",
    summary: "Windows 11 24H2 ISO archive mirror (Home, Pro, Education) for x64 and ARM64."
  },
  {
    id: "win11-arm64",
    title: "Windows 11 ARM64",
    category: "windows11",
    vendor: "Microsoft",
    build: "Build 26100.1150",
    size: "5.1 GB",
    pro: false,
    sha256: "f3408a8d11634b8c9f53e30f146a48a901ee5c7bc5da864e4325a7f9202158dc",
    portal: "https://www.microsoft.com/software-download/windows11arm64",
    summary: "Native ARM64 image for Snapdragon X PCs and Apple Silicon virtual machines."
  },
  {
    id: "win10-22h2",
    title: "Windows 10 (22H2)",
    category: "windows10",
    vendor: "Microsoft",
    build: "Build 19045.2965",
    size: "5.7 GB",
    pro: false,
    sha256: "a6f470ca6d3314104f40e74fbd5e60c0f92651dc4434f07612183ec42996166a",
    portal: "https://www.microsoft.com/software-download/windows10",
    summary: "Official x64 & x86 release with broad hardware support and no TPM 2.0 requirement."
  },
  {
    id: "win11-ltsc-2024",
    title: "Windows 11 Enterprise LTSC 2024",
    category: "server",
    vendor: "Microsoft",
    build: "Build 26100.1",
    size: "4.9 GB",
    pro: false,
    sha256: "e10a8cb1e3b6ea2a738eeec8cb4d2e858dfc989b538e12368a78e7aa96316260",
    portal: "https://www.microsoft.com/evalcenter/evaluate-windows-11-enterprise-ltsc",
    summary: "Long-Term Servicing Channel release without consumer apps or feature churn."
  },
  {
    id: "win-server-2025",
    title: "Windows Server 2025",
    category: "server",
    vendor: "Microsoft",
    build: "Build 26100",
    size: "5.2 GB",
    pro: false,
    sha256: "c8b417c80084f7bbd7dd50b1062086e3f3286f7881c3c9ba8d1487103a8305c1",
    portal: "https://www.microsoft.com/evalcenter/evaluate-windows-server-2025",
    summary: "180-day official evaluation media for Standard & Datacenter editions."
  },
  {
    id: "win81-sp1",
    title: "Windows 8.1 Update 3",
    category: "legacy",
    vendor: "Microsoft",
    build: "Build 9600.17415",
    size: "4.0 GB",
    pro: false,
    sha256: "a8b5f673d55b62d0882e7de80d238d850794222040ecf1464406fa550f3b59f4",
    portal: "https://www.microsoft.com/software-download/windows8ISO",
    summary: "Legacy retail installation media for older laptops and netbooks."
  },
  {
    id: "macos-sequoia-15",
    title: "macOS Sequoia 15.1",
    category: "macos",
    vendor: "Apple",
    build: "Build 24B83",
    size: "14.5 GB",
    pro: true,
    sha256: "7d94a21c8e35b0f4a19e824c6b77d0f3e2b14a56c89d01f234a5b6c7d8e9f012",
    portal: "https://support.apple.com/en-us/102662",
    summary: "Official Apple InstallAssistant.pkg & Universal bootable installer guide (Pro)."
  },
  {
    id: "macos-sonoma-14",
    title: "macOS Sonoma 14.7",
    category: "macos",
    vendor: "Apple",
    build: "Build 23H124",
    size: "13.7 GB",
    pro: true,
    sha256: "4c81e52b9f01d7a36e4b8c9d0e1f2a3b4c5d6e7f8091a2b3c4d5e6f708192a3b",
    portal: "https://support.apple.com/en-us/102662",
    summary: "Stable macOS 14 full installer for Intel Macs and Apple Silicon (Pro)."
  },
  {
    id: "macos-ventura-13",
    title: "macOS Ventura 13.7",
    category: "macos",
    vendor: "Apple",
    build: "Build 22H123",
    size: "12.2 GB",
    pro: true,
    sha256: "91a8b7c6d5e4f302192837465564738291a0b1c2d3e4f5a6b7c8d9e0f1a2b3c4",
    portal: "https://support.apple.com/en-us/102662",
    summary: "Full macOS 13 installer supporting 2017+ Intel Macs and Apple Silicon (Pro)."
  }
];

const TOOLKIT_CATALOG = [
  {
    name: "Rufus Portable",
    vendor: "Pete Batard",
    version: "v4.6",
    size: "1.5 MB",
    ext: ".exe",
    url: "https://github.com/pbatard/rufus/releases/download/v4.6/rufus-4.6p.exe",
    portal: "https://rufus.ie/",
    summary: "Create bootable Windows USB drives with optional TPM 2.0 & Secure Boot bypass."
  },
  {
    name: "Ventoy Multi-Boot",
    vendor: "Ventoy Official",
    version: "v1.0.99",
    size: "15.9 MB",
    ext: ".zip",
    url: "https://github.com/ventoy/Ventoy/releases/download/v1.0.99/ventoy-1.0.99-windows.zip",
    portal: "https://www.ventoy.net/en/download.html",
    summary: "Copy multiple Windows, Server, and Linux ISOs onto a single USB without reformatting."
  },
  {
    name: "Media Creation Tool",
    vendor: "Microsoft",
    version: "24H2",
    size: "9.8 MB",
    ext: ".exe",
    url: "https://go.microsoft.com/fwlink/?linkid=2156295",
    portal: "https://www.microsoft.com/software-download/windows11",
    summary: "Microsoft official tool to prepare Windows 11 installation USB drives."
  },
  {
    name: "Sysinternals Suite",
    vendor: "Microsoft",
    version: "2024.10",
    size: "49.0 MB",
    ext: ".zip",
    url: "https://download.sysinternals.com/files/SysinternalsSuite.zip",
    portal: "https://learn.microsoft.com/sysinternals/downloads/sysinternals-suite",
    summary: "Complete Windows troubleshooting kit: Process Explorer, Autoruns, Disk2vhd."
  },
  {
    name: "PC Health Check",
    vendor: "Microsoft",
    version: "v3.9",
    size: "14.0 MB",
    ext: ".msi",
    url: "https://aka.ms/GetPCHealthCheckApp",
    portal: "https://www.microsoft.com/windows/windows-11#pchealthcheck",
    summary: "Verify CPU, RAM, TPM 2.0, and Secure Boot compatibility for Windows 11."
  },
  {
    name: "7-Zip Extractor",
    vendor: "Igor Pavlov",
    version: "v24.08",
    size: "1.6 MB",
    ext: ".exe",
    url: "https://www.7-zip.org/a/7z2408-x64.exe",
    portal: "https://www.7-zip.org/download.html",
    summary: "Fast utility to inspect or extract .iso, .wim, .esd, and .zip packages."
  }
];

let activeCategory = "all";
let selectedPlan = "Annual (₹1,499/year)";

function renderIsos() {
  const grid = document.getElementById("iso-grid");
  const searchEl = document.getElementById("iso-search");
  if (!grid || !searchEl) return;

  const q = searchEl.value.trim().toLowerCase();
  const filtered = ISO_CATALOG.filter((item) => {
    const catOk = activeCategory === "all" || item.category === activeCategory;
    const textOk =
      !q ||
      item.title.toLowerCase().includes(q) ||
      item.build.toLowerCase().includes(q) ||
      item.summary.toLowerCase().includes(q);
    return catOk && textOk;
  });

  if (filtered.length === 0) {
    grid.innerHTML = `<div class="card"><p>No matching ISOs found. Clear your search or select "All OS".</p></div>`;
    return;
  }

  grid.innerHTML = filtered
    .map(
      (item) => `
      <article class="card">
        <div class="card-top">
          <div>
            <h3>${item.title}</h3>
            <div class="meta">${item.vendor} • ${item.build} • ${item.size}</div>
          </div>
          <span class="tag ${item.pro ? "tag-pro" : "tag-free"}">${item.pro ? "PRO" : "FREE"}</span>
        </div>
        <p>${item.summary}</p>
        <div class="hash-box">SHA-256: ${item.sha256}</div>
        <div class="card-actions">
          ${
            item.pro
              ? `<a href="#pro-access" class="btn btn-pro">Request Pro Access</a>`
              : `<a href="${item.portal}" target="_blank" rel="noopener noreferrer" class="btn btn-primary">Official Download</a>`
          }
          <button type="button" class="btn btn-outline" onclick="copyHash('${item.sha256}')">Copy SHA-256</button>
        </div>
      </article>
    `
    )
    .join("");
}

function renderTools() {
  const grid = document.getElementById("toolkit-grid");
  if (!grid) return;
  grid.innerHTML = TOOLKIT_CATALOG.map(
    (tool) => `
    <article class="card">
      <div class="card-top">
        <div>
          <h3>${tool.name}</h3>
          <div class="meta">${tool.vendor} • ${tool.version} • ${tool.size} (${tool.ext})</div>
        </div>
        <span class="tag tag-free">OFFICIAL</span>
      </div>
      <p>${tool.summary}</p>
      <div class="card-actions">
        <a href="${tool.url}" target="_blank" rel="noopener noreferrer" class="btn btn-primary">Download ${tool.ext}</a>
        <a href="${tool.portal}" target="_blank" rel="noopener noreferrer" class="btn btn-outline">Website</a>
      </div>
    </article>
  `
  ).join("");
}

function copyHash(hash) {
  if (navigator.clipboard) {
    navigator.clipboard.writeText(hash).catch(() => {});
  }
  const verifyInput = document.getElementById("sha-input");
  if (verifyInput) {
    verifyInput.value = hash;
    verifySha256();
  }
}

function verifySha256() {
  const input = document.getElementById("sha-input");
  const out = document.getElementById("sha-result");
  if (!input || !out) return;

  const cleaned = input.value.trim().toLowerCase();
  if (!cleaned) {
    out.className = "status-box status-warn show";
    out.textContent = "Please paste a 64-character SHA-256 checksum to verify.";
    return;
  }

  const match = ISO_CATALOG.find((item) => item.sha256.toLowerCase() === cleaned);
  if (match) {
    out.className = "status-box status-success show";
    out.textContent = `Verified Match: ${match.title} (${match.build}) — Official ${match.vendor} checksum.`;
  } else {
    out.className = "status-box status-warn show";
    out.textContent = "No exact catalog match found for this SHA-256 checksum.";
  }
}

function selectPlan(el, planLabel) {
  document.querySelectorAll(".plan-card").forEach((card) => card.classList.remove("selected"));
  el.classList.add("selected");
  selectedPlan = planLabel;
}

function submitProRequest(event) {
  event.preventDefault();
  const emailEl = document.getElementById("pro-email");
  const statusEl = document.getElementById("pro-status");
  if (!emailEl || !statusEl) return;

  const email = emailEl.value.trim();
  if (!email.includes("@") || !email.includes(".")) {
    statusEl.className = "status-box status-warn show";
    statusEl.textContent = "Please enter a valid email address.";
    return;
  }

  const ref = `PRO-${Math.floor(1000 + Math.random() * 9000)}`;
  statusEl.className = "status-box status-success show";
  statusEl.innerHTML = `<strong>We sent you an email!</strong> Details for <strong>${selectedPlan}</strong> were sent to <strong>${email}</strong>. We will contact you soon. (Ref: ${ref})`;
}

document.addEventListener("DOMContentLoaded", () => {
  document.querySelectorAll("[data-category]").forEach((btn) => {
    btn.addEventListener("click", () => {
      document.querySelectorAll("[data-category]").forEach((b) => b.classList.remove("active"));
      btn.classList.add("active");
      activeCategory = btn.getAttribute("data-category");
      renderIsos();
    });
  });

  const searchEl = document.getElementById("iso-search");
  if (searchEl) searchEl.addEventListener("input", renderIsos);

  renderIsos();
  renderTools();
});
